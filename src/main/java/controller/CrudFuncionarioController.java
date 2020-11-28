package controller;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.omg.CORBA.INITIALIZE;

import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ConnectionFactory;
import model.Funcionario;
import model.FuncionarioDAO;

public class CrudFuncionarioController implements Initializable {
	
	

	
	
    @FXML
    private TextField txtNovoFuncionario;

    @FXML
    private DatePicker txtDataNascimentoNovoFuncionario;

    @FXML
    private TextField txtCargoNovoFuncionario;

    @FXML
    private TextField txtSalarioNovoFuncionario;

    @FXML
    private Tab Cadastrar;
    
    @FXML
    private Tab Consultar;
    
    @FXML
    private Tab Atualizar;

    @FXML
    private TextField txtNomeConsultaFuncionario;

    @FXML
    private TableView<Funcionario> tblFuncionario;

    @FXML
    private TableColumn<Funcionario, Integer> colIdFuncionario;

    @FXML
    private TableColumn<Funcionario, Date> colDataNascFuncionario;
    
    @FXML
    private TableColumn<Funcionario, BigDecimal> colSalarioFuncionario;
    
    @FXML
    private TableColumn<Funcionario, String> colNomeFuncionario;

    @FXML
    private TableColumn<Funcionario, String> colCargoFuncionario;
    
    @FXML
	 private TabPane abas;

   public void Initialize (URL arg0, ResourceBundle arg1) {
    	
    	dao = new FuncionarioDAO();
    	
    	//colIdFuncionario.setCellValueFactory(new PropertyValueFactory<>("id"));
    	//txtNomeConsultaFuncionario.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	//colDataNascFuncionario.setCellValueFactory(new PropertyValueFactory<>("DataNascimento"));
    	//colCargoFuncionario.setCellValueFactory(new PropertyValueFactory<>("cargo"));
    	//colSalarioFuncionario.setCellValueFactory(new PropertyValueFactory<>("salario"));
    	
    	exibirDialogoInformacao("Teste Informação");

    }


    @FXML
    private TextField txtFuncionario;

    @FXML
    private DatePicker txtDataNascimentoFuncionario;

    @FXML
    private TextField txtCargoFuncionario;

    @FXML
    private TextField txtSalarioFuncionario;
    
    private FuncionarioDAO dao;

    private Funcionario funcionarioSelecionado; 
   
    @FXML
    void btnConsultarFuncionario() {
    	
    	try {
    		
    		List<Funcionario> resultado = dao.consultar(txtNomeConsultaFuncionario.getText());
        	
        	if (resultado.isEmpty()){
                 exibirDialogoInformacao("Nenhum funcionario encontrado! ");
            } 
        	else {
        		
        		tblFuncionario.setItems(FXCollections.observableArrayList(resultado));
                 
            }
			
		} catch (Exception e) {
			exibirDialogoErro("Falha ao realizar a consulta! ");
			e.printStackTrace();
		}
    	
    	
    	

    }

    @FXML
    void btnDeletarFuncionario() {
    	
    	if (tblFuncionario.getSelectionModel().getSelectedItem() == null) {
    		 exibirDialogoErro("Não há funcionario selecionado");
        } else {
        	
        	if (exibirDialogoConfirmacao("Confirma a exclusão do funcionario selecionado?"));
        	
        	try {
				dao.deletar(tblFuncionario.getSelectionModel().getSelectedItem().getId());
				exibirDialogoInformacao("Funcionario deletado com sucesso!");
				btnConsultarFuncionario();
			} catch (Exception e) {
				exibirDialogoErro("Falha ao deletar o funcionario.");
				e.printStackTrace();
			}
        }

    }
    
    @FXML
    void btnSalvarFuncionario() {

    	funcionarioSelecionado.setNome(txtFuncionario.getText());
    	funcionarioSelecionado.setDataNascimento(Date.valueOf(txtDataNascimentoFuncionario.getValue()));
    	funcionarioSelecionado.setCargo(txtCargoFuncionario.getText());
    	funcionarioSelecionado.setSalario(new BigDecimal(txtSalarioFuncionario.getText()));
    	
    	try {
    		
    		dao.atualizar(funcionarioSelecionado);
    		exibirDialogoInformacao("Funcionario Atualizado com sucesso! ");
    		abas.getSelectionModel().select(Consultar);
    		btnConsultarFuncionario();
		} catch (Exception e) {
			exibirDialogoErro("Falha ao atualizar o funcionario");
			e.printStackTrace();
		}
    }

    @FXML
    void btnExibirAbaAtualizacao() {
        
    	funcionarioSelecionado = tblFuncionario.getSelectionModel().getSelectedItem();
    	
    	if (tblFuncionario.getSelectionModel().getSelectedItem() == null) {
   		 exibirDialogoErro("Não há funcionario selecionado");
       } 
    	else {
    		Atualizar.setDisable(false);       
    		abas.getSelectionModel().select(Atualizar);
    		txtFuncionario.setText(funcionarioSelecionado.getNome());		
    		txtDataNascimentoFuncionario.setValue(funcionarioSelecionado.getDataNascimento().toLocalDate());
    		txtCargoFuncionario.setText(funcionarioSelecionado.getCargo());
    		txtSalarioFuncionario.setText(funcionarioSelecionado.getSalario().toString());
    	}
    }

    @FXML
    void btnLimparNovoFuncionario() {
    	
    	txtNovoFuncionario.clear();
    	//txtDataNascimentoNovoFuncionario.clear();
    	txtCargoNovoFuncionario.clear();
    	txtSalarioNovoFuncionario.clear();
    	
    }

    
    private void exibirDialogoInformacao(String informacao) {

    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("informação");
    	alert.setHeaderText(null);
    	alert.setContentText(informacao);
    	
    	alert.showAndWait();
    }
    
    private void exibirDialogoErro(String erro) {

    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Erro");
    	alert.setHeaderText(null);
    	alert.setContentText(erro);
    	
    	alert.showAndWait();
    }
    
    private boolean exibirDialogoConfirmacao(String confirmacao) {

    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmação");
    	alert.setHeaderText(null);
    	alert.setContentText(confirmacao);
    	
    	Optional<ButtonType> opcao = alert.showAndWait();
    	
    	if (opcao.get() == ButtonType.OK)
    		return true;
    	
    	return false;
    }
    
    private void limparCadastroAtualizacaoFuncionario(){
    	
    	txtFuncionario.clear();
    	//txtDataNascimentoFuncionario.clear();
    	txtCargoFuncionario.clear();
    	txtSalarioFuncionario.clear();

    }

    @FXML
    void btnSalvarNovoFuncionario() {
    	
    	Funcionario funcionario = new Funcionario();
    	
    	funcionario.setNome(txtNovoFuncionario.getText());
    	funcionario.setDataNascimento(Date.valueOf(txtDataNascimentoNovoFuncionario.getValue()));
    	funcionario.setCargo(txtCargoNovoFuncionario.getText());
    	funcionario.setSalario(new BigDecimal(txtSalarioNovoFuncionario.getText()));
    	
    	
    	try {
			
    		dao.cadastrar(funcionario);
    		exibirDialogoInformacao("Funcionario cadastro com sucesso!");
    		limparCadastroAtualizacaoFuncionario();
		} catch (Exception e) {
			exibirDialogoErro("Falha ao cadastrar o funcionario.");
			e.printStackTrace();
		}
    	

    }

    @FXML
    void gerenciarAbas() {
    	
    	if (Cadastrar.isSelected() || Consultar.isSelected()) {
    		Atualizar.setDisable(true);
    		limparCadastroAtualizacaoFuncionario();
    		
    	}

    }

	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			Connection connection = new ConnectionFactory().getConnection();
			System.out.println("Sucesso!");
			connection.close();
		} catch (Exception e) {
			System.out.println("Falha na conexão!");
			e.printStackTrace();
		}
		
		

	}



}
