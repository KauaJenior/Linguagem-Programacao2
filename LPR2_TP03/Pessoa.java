import org.omg.PortableServer.IdAssignmentPolicy;

public class Pessoa{
 private static int kp = 0;
 private String nome;
 private char sexo;
 private int idade;

 
 
 
 
 public Pessoa(){};

 public Pessoa(String Nome, char Sexo, int Idade){

    this.nome = Nome;
    this.sexo = Sexo;
    this.idade = Idade;

    setKp();
 }

 public void setKp(){
  kp++;
 }

 public String setNome(String n){
   this.nome = n;

   return n;
 }

 public char setSexo(char s){
    this.sexo = s;

    return sexo;
 }

 public int setIdade(int i){
    this.idade = i;
    return idade;
 }

 public int getKp(){
    return kp;
 }
    
 public String getNome(){
    return nome;
 }

 public char getSexo(){
    return sexo;
 }

 public int getIdade(){
    return idade;
 }
 
}