package Arquitetura;

import java.util.UUID;

import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import java.util.ArrayList;

import lombok.Data;

public class Function {

    @FunctionName ("helloserverless")
    public String hello(
        @HttpTrigger (
            name = "hellorest",
            methods = {HttpMethod.GET},
            route = "hello"
        )
        String x) {

		return "Hello REST World!";
	}

    @FunctionName ("createFunc")
    public Funcionario create (
        @HttpTrigger (
            name = "createFuncRest",
            methods = {HttpMethod.POST},
            route = "funcionario"
        ) 
        Funcionario funcionario) {
        
            FuncionarioDAO funDAO = new FuncionarioDAO();
            funcionario.setId(UUID.randomUUID());
            
            funDAO.create(funcionario);

            return funcionario;
	}
    @FunctionName ("updateFunc")
	public Funcionario update (
        @HttpTrigger (
            name = "updateFuncrest",
            methods = {HttpMethod.PUT},
            route = "funcionario"
        ) 
        Funcionario funcionario) {
                
            FuncionarioDAO funcDAO = new FuncionarioDAO();
            funcionario = funcDAO.update(funcionario);
            
            return funcionario;
    }

    @FunctionName ("searchFunc")
	public ArrayList<Funcionario> search (
        @HttpTrigger (
            name = "searchFuncRest",
            methods = {HttpMethod.GET},
            route = "funcionario"
        ) 
        Funcionario funcionario) {
            ArrayList<Funcionario> array = new ArrayList<>();
            FuncionarioDAO funcDAO = new FuncionarioDAO();
            array = funcDAO.searchAll();
            
            
            return array;
    }
    
        @FunctionName ("deleteFunc")
	public ArrayList<Funcionario> delete (
        @HttpTrigger (
            name = "deleteFuncRest",
            methods = {HttpMethod.DELETE},
            route = "funcionario"
        ) 
        Funcionario funcionario) {
            FuncionarioDAO funcDAO = new FuncionarioDAO();
            
            if (funcDAO.delete(funcionario)) {
                return funcDAO.searchAll();
            }
            
            return null;
    }
}

@Data
class Funcionario{
	private UUID id;
	private String name;
	private int age;
        private double salario;
}

class FuncionarioDAO{
    
    private ArrayList<Funcionario> array = new ArrayList<>();
   
    public void create(Funcionario funcionario){
       array.add(funcionario);
    }
    public ArrayList<Funcionario> searchAll(){
        return array;
    }
    
    public Funcionario search(Funcionario funcionario){
        
        for (int i = 0; i < array.size(); i++) {
            if (funcionario.getName().equals(array.get(i).getName())) {
               return array.get(i);
            }
        }
       
        return null;
    }
    
    public Funcionario update(Funcionario funcionario){
        int index =0;
        for (int i = 0; i < array.size(); i++) {
            if (funcionario.getId() == array.get(i).getId()) {
                index =i ;
                array.add(index, funcionario);
            }            
        }
        
        return array.get(index);
    }
    public boolean delete(Funcionario funcionario){
        int index =0;
        for (int i = 0; i < array.size(); i++) {
            if (funcionario.getId() == array.get(i).getId()) {
                index =i ;
            }            
        }
        try {
            array.remove(index);
            return true;
        } catch (Exception e) {
            System.out.println("Deu Ruim");
        }
        return false;
    }
    
}
