
import br.com.lanchonete.produtos.*;
import br.com.lanchonete.pessoas.*;
import java.util.Scanner;
import java.io.IOException;
import java.util.Locale;
import java.util.Collections;


/**
 * Classe principal para o Sistema da Lanchonete contendo o método main()
 *
 * @author Mateus Henrique Machado
 * @author Iago Mateus Ávila Fernandes
 * @version 2.0
 */
//Questão 1 - Implementar todas as classes com base no diagrama de classes criado
public class SistemaLanchonete {

    public static void main(String[] args) throws IOException {

        Locale locale = new Locale("pt", "BR");
        Locale.setDefault(locale);

        // O TESTE INDIVIDUAL DE CHAMADAS DE FUNÇÕES SE INICIA NA LINHA 73
        
        /* PARA USO DA FUNÇÃO startSistema(), retire-a do comentário na linha 38
        ACESSO: 
        ADMINISTRADOR:
            login: admin
            senha: admin
        COLABORADOR
            login: colab1
            senha: colab1   */
        
        
        //RETIRE AQUI \/
        // SistemaLanchonete.startSistema(); 
        
        
        
        /*
        INTERFACE GRÁFICA 
        para inicializá-la, retire do comentário as linhas 47 e 48  */
        
        //Retire aqui \/
        //TelaLogin startInterface = new TelaLogin();
        //startInterface.setVisible(true);
        
        
        
        
        
        /*
        TESTES DE FUNÇÕES 
        -------------------------------------
        As chamadas a seguir representam testes de funções conforme pedido pelas orientações do trabalho e que 
        se deram na forma de construção de funções e/ou métodos. Nesse sentido, o teste está coerente com 
        as restrições de uso a cada tipo de usuário, sendo que o login como Administrador permitirá
        funções adequadas a ele e o mesmo para colaborador. Caso deseje acessar as outras funções, reinicie o 
        sistema e realize um novo login com um tipo de usuário diferente. 
        
        PARA O TESTE DE ACESSO: 
        
        ADMINISTRADOR:
            login: admin
            senha: admin
        
        COLABORADOR
            login: colab1
            senha: colab1
         */
        System.out.println("""
                           \nTESTE DE CHAMADAS INDIVIDUAIS DE FUNÇÕES
                           Obs.: Chamadas individuais podem levar a comportamentos um pouco diferentes daqueles objetivados 
                           com o funcionamento do programa como um sistema completo.\n
                           
                           """);

        manipularJson mJson = new manipularJson();

        //Questão 13 - Recuperar informações de um arquivo Json
        mJson.assimilateAll();

        ProxyAdministrador menuAdm = new ProxyAdministrador();
        ProxyColaborador menuColab = new ProxyColaborador();
        Usuario usuarioAtual = new Usuario();

        //REALIZAR LOGIN NO SISTEMA
        System.out.println("LOGIN: ");
        Scanner inputLogin = new Scanner(System.in);
        System.out.printf("E- mail: ");
        String loginUsuario = inputLogin.nextLine();
        System.out.printf("Senha: ");
        String senhaUsuario = inputLogin.nextLine();
        usuarioAtual = SistemaLanchonete.loginSistema(mJson, loginUsuario, senhaUsuario);

        //Questão 2 - O sistema deve ser utilizado por um administrador ou colaborador
        if (usuarioAtual instanceof Administrador) {
            System.out.println("\nTipo de usuário: Administrador    " + usuarioAtual);

            //Questão 6
            //Cadastro de cliente
            System.out.println("\nCADASTRO DE CLIENTE\n________________________");
            menuAdm.cadastroCliente();

            //Questão 6
            //Alterar dados de cliente
            System.out.println("\nALTERAR DADOS DE CLIENTE\n_______________________");
            Scanner input = new Scanner(System.in);
            String CPFCliente;

            do {
                System.out.println("Entre com um CPF válido de cliente: ");
                CPFCliente = input.nextLine();
            } while (ProxyAdministrador.ValidaCPF(CPFCliente) == false);

            menuAdm.modificarCliente(CPFCliente);

            //Questão 7 - Verificar dados de pedidos de clientes
            //Opção 5 - Listar pedidos
            System.out.println("\nLISTAR PEDIDOS\n_____________________");
            System.out.println("CPF do cliente: ");
            String CPF = input.nextLine();
            if (ProxyAdministrador.consultaCliente(CPF) != null) {
                if (ProxyAdministrador.consultaCliente(CPF).getPedidosCliente().size() >= 1) {
                    System.out.println("Lista de pedidos do Cliente: ");
                    //Questão 12 - Implementar a interface Comparator para as classes Cliente e Pedido
                    Collections.sort(ProxyAdministrador.consultaCliente(CPF).getPedidosCliente(), new PedidoComparator());
                    for (int k = 0; k < ProxyAdministrador.consultaCliente(CPF).getPedidosCliente().size(); k++) {
                        System.out.println(ProxyAdministrador.consultaCliente(CPF).getPedidosCliente().get(k));
                    }

                } else {
                    System.out.println("Não há pedidos cadastrados para este cliente.");

                }

            } else {
                System.out.println("CPF inválido ou cliente não cadastrado.");
            }

            //Questão 12 
            //Comparator clientes - Listagem dos clientes cadastrados 
            System.out.println("\nORDENAÇÃO DE CLIENTES POR CPF\n_______________________");
            ClienteComparator comparator = new ClienteComparator();
            Collections.sort(ProxyAdministrador.getClientes(), comparator);
            for (Cliente cliente : ProxyAdministrador.getClientes()) {
                if (cliente != null) {
                    System.out.println(cliente);

                }
            }

            //Questão 10 e 11
            //Uso da variável privada
            //Método "getQntClientesPrivate" - Questão 11 a
            ProxyAdministrador.setQntClientesPrivate();
            System.out.println("\nQuantidade de clientes cadastrados: " + ProxyAdministrador.getQntClientesPrivate());

            //Uso da variável protected
            //Método "getClientesProtected" - Questão 11 b
            ProxyAdministrador.setQntClientesProtected();
            System.out.println("\nQuantidade de clientes cadastrados: " + ProxyAdministrador.qntClientesProtected);

            //Questão 2 - Uso por colaborador
        } else if (usuarioAtual instanceof Colaborador) {
            System.out.println("Tipo de usuário: Colaborador     " + usuarioAtual);

            //Questão 9
            // Extrato gerado no cadastro do pedido - Opção 1
            System.out.println("\nCADASTRO DE PEDIDO\n____________________________");
            menuColab.cadastroPedido();

            //Questão 9
            //Listar todos os extratos cadastrados
            System.out.println("\nEXTRADOS DE PEDIDOS CADASTRADOS\n_____________________________");
            mJson.dumpExtratosPedidos(menuColab.extratosPedidos());
            ProxyColaborador.setExtratosPedidos(mJson.assimilateExtratosPedidos());
            for (String extratoPedido : ProxyColaborador.getExtratosPedidos()) {
                System.out.println(extratoPedido);

            }

            //Questão 12
            //Comparator pedidos
            System.out.println("\nORDENAÇÃO DE PEDIDOS\n_______________________");
            System.out.println("CPF do cliente: ");
            Scanner input = new Scanner(System.in);
            String CPF = input.nextLine();
            System.out.println("Lista de pedidos do Cliente: ");
            Collections.sort(ProxyAdministrador.consultaCliente(CPF).getPedidosCliente(), new PedidoComparator());
            for (int k = 0; k < ProxyAdministrador.consultaCliente(CPF).getPedidosCliente().size(); k++) {
                System.out.println(ProxyAdministrador.consultaCliente(CPF).getPedidosCliente().get(k));
            }

            //Questão 14 - Pesquisar pedidos em um intervalo de tempo
            System.out.println("\nLISTAR PEDIDOS\n_________________________");
            menuColab.listarPedidos();

            //Estatísticas de vendas
            System.out.println("\nESTATÍSTICAS DE VENDAS\n_____________________________");
            menuColab.statsVendas();

        } else {
            System.out.println("Login ou senha inválidos. Tente novamente!");
        }

        //Questão 13
        //Salvar informações
        mJson.dumpAll(menuColab);
        if (usuarioAtual instanceof Administrador) {
            mJson.dumpAdministrador((Administrador) usuarioAtual);
        }

    }

    /**
     * Função padrão para login no sistema
     *
     * @param mJson Objeto da classe manipularJson para assimilação de dados
     * @param txtLogin String que representa o login do usuário
     * @param txtSenha String que representa a senha do usuário
     * @return Usuario logado no sistema desde que as credenciais estejam
     * cadastradas
     * @throws IOException Exceção associada à manipulação de arquivos Json
     */
    //Questão 2 - O sistema será utilizado por colaboradores e pelo administrador
    public static Usuario loginSistema(manipularJson mJson, String txtLogin, String txtSenha) throws IOException {
        //Questão 13 - Salve e recupere todas as informações do sistema em um arquivo json
        Administrador adm = mJson.assimilateAdministrador();
        mJson.assimilateAll();

        String loginUsuario = txtLogin;
        String senhaUsuario = txtSenha;

        Usuario usuarioAtual = null;

        if (loginUsuario.equals(adm.getLoginUsuario()) && senhaUsuario.equals(adm.getSenhaUsuario())) {
            usuarioAtual = adm;

        } else {
            for (Colaborador colab : ProxyAdministrador.getColaboradores()) {
                if (colab != null) {
                    if (loginUsuario.equals(colab.getLoginUsuario()) && senhaUsuario.equals(colab.getSenhaUsuario())) {
                        usuarioAtual = colab;
                        break;
                    }

                }

            }
        }

        if (usuarioAtual == null) {
            System.out.println("Login inválido. Tente novamente");
        }
        return usuarioAtual;

    }

    /**
     * Função padrão de inicialização do sistema como um todo
     *
     * @throws IOException Exceção associada à manipulação de dados Json
     */
    public static void startSistema() throws IOException {
        //Uso de json
        //Questão 13 - Salve e recupere todas as informações do sistema em um arquivo json
        manipularJson mJson = new manipularJson();
        mJson.assimilateAll();

        Scanner input = new Scanner(System.in);

        Usuario usuarioAtual = null;

        //Inicializando sistema
        ProxyAdministrador menuAdm = new ProxyAdministrador();
        ProxyColaborador menuColab = new ProxyColaborador();
        ProxyColaborador.setExtratosPedidos(mJson.assimilateExtratosPedidos());
        MenuSistema menuSistema = new MenuSistema();

        //Login
        Scanner inputSistema = new Scanner(System.in);
        int i;
        boolean sairSistema = false;

        System.out.printf("""
                           
                           Escolha uma opção:
                           _________________________________________
                           1 -  Login          
                           2 -  Sair
                           _________________________________________
                               """);
        inputSistema = new Scanner(System.in);
        try {
            i = inputSistema.nextInt();
        } catch (Exception e) {
            i = 5;
        }

        //Questão 2 - O sistema será utilizado por colaboradores e pelo administrador
        switch (i) {
            case 1 -> {
                while (usuarioAtual == null) {
                    System.out.printf("E- mail: ");
                    String loginUsuario = input.nextLine();
                    System.out.printf("Senha: ");
                    String senhaUsuario = input.nextLine();
                    usuarioAtual = SistemaLanchonete.loginSistema(mJson, loginUsuario, senhaUsuario);
                }

                if (usuarioAtual instanceof Administrador) {
                    menuSistema.menuAdministrador(menuAdm, menuColab, usuarioAtual);
                    //Questão 13 - Salve e recupere todas as informações do sistema em um arquivo json
                    mJson.dumpAdministrador((Administrador) usuarioAtual);

                } else if (usuarioAtual instanceof Colaborador) {
                    menuSistema.menuColaborador(menuAdm, menuColab, usuarioAtual);
                }
                break;
            }
            case 2 -> {
                //Questão 13 - Salve e recupere todas as informações do sistema em um arquivo json
                mJson.dumpAll(menuColab);
                sairSistema = true;
                break;
            }
            default -> {
                System.out.println("Entrada invalida. Opção default selecionada... Encerrando sistema!");
            }
        }

    }

    /**
     *
     * @return Representação String do Sistema da Lanchonete
     */
    //Questão 3 - Sobrescrever o método toString() de todas as classes implementadas
    @Override
    public String toString() {
        return "SistemaLanchonete";
    }

}
