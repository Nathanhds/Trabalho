package Trabalho;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Trabalho2 {

    public static void CreateNewTask(String[][] matriz, int lines, int columns) {
        Scanner scanner = new Scanner(System.in);
        int linha;
        String data;
        String taskName;
        int option;
        int editOption;
        boolean taskExist = false;


        ShowTasks(matriz, lines, columns);
        do {
            System.out.println("Informe onde você deseja inserir o produto com valores de 1 à " + lines);
            linha = scanner.nextInt();
            if(linha < 1 || linha > lines) {
                System.out.println("Local inválido!");
            }
        } while (linha < 1 || linha > lines);
        System.out.println("Insira o nome da nova tarefa: ");
        taskName = scanner.next();
        for (int i = 0; i < lines; i++) {
            if (matriz[i][0] != null) {
                if (matriz[i][0].equals("Nome: " + taskName)) {
                    System.out.println("Já existe uma tarefa com este nome!");
                    taskExist = true;
                    do {
                        System.out.println("Deseja editar?");
                        System.out.println("1 - Sim");
                        System.out.println("2 - Não");
                        editOption = scanner.nextInt();
                        if(editOption < 1 || editOption > 2) {
                            System.out.println("Opção Inválida");
                        }
                    } while(editOption < 1 || editOption > 2);
                    if (editOption == 1) {
                        EditTask(matriz, lines, taskName);
                    }
                    else {
                        System.out.println("Operação cancelada!");
                    }
                }
            }
        }


        if (!taskExist) {
            matriz[linha - 1][0] = "Nome: " + taskName;
            System.out.println("Insira a descrição da nova tarefa:");
            matriz[linha - 1][1] = "Descrição: " + scanner.next();
            System.out.println("Insira a data da nova tarefa:");
            data = scanner.next();
            DataInsert(matriz, data, linha - 1);
            do {
                System.out.println("Escolha o Status da nova tarefa:");
                System.out.println("1 - A Fazer");
                System.out.println("2 - Fazendo");
                System.out.println("3 - Completa");
                option = scanner.nextInt();
                if(option < 1 || option > 3) {
                    System.out.println("Opção inválida!");
                }
                Space();
            } while (option < 1 || option > 3);
            if (option == 1) {
                matriz[linha - 1][3] = "Status: A Fazer";
            } else if (option == 2) {
                matriz[linha - 1][3] = "Status: Fazendo";
            } else {
                matriz[linha - 1][3] = "Status: Completa";
            }
        }





    }

    public static void ShowTasks(String[][] matriz, int lines, int columns) {

        for (int i = 0; i < lines; i++) {
            System.out.print((i + 1) + "  -  ");
            for (int j = 0; j < columns; j++) {
                System.out.print(matriz[i][j] + " | ");
            }
            System.out.println();
        }
    }


    public static void EditTask(String[][] matriz, int lines, String nomeTarefa) {
        Scanner scanner = new Scanner(System.in);
        boolean search = false;
        int opcao;
        String alteracao;
        for (int i = 0; i < lines; i++) {
            if (matriz[i][0] != null && matriz[i][0].equals("Nome: " + nomeTarefa)) {
                search = true;
                do {
                    System.out.println(matriz[i][0] + " | " + matriz[i][1] + " | " + matriz[i][2] + " | " + matriz[i][3]);
                    System.out.println("O quê você deseja editar");
                    System.out.println("1 - Nome");
                    System.out.println("2 - Descrição");
                    System.out.println("3 - Data");
                    opcao = scanner.nextInt();
                    Space();
                } while (opcao < 1 || opcao > 3);

                switch (opcao) {
                    case 1:
                        System.out.println("Insira o novo nome para a tarefa " + matriz[i][0]);
                        alteracao = scanner.next();
                        matriz[i][0] = "Nome: " + alteracao;
                        Space();
                        break;

                    case 2:
                        System.out.println("Insira a nova descrição para a tarefa " + matriz[i][0]);
                        alteracao = scanner.next();
                        matriz[i][1] = "Descrição: " + alteracao;
                        Space();
                        break;

                    case 3:
                        System.out.println("Insira a nova data para a tarefa " + matriz[i][0]);
                        alteracao = scanner.next();
                        DataInsert(matriz, alteracao, i);
                        Space();
                        break;

                    default:
                        System.out.println("Opção Inválida!");
                        Space();
                }
            }
        }
        if (search) {
            System.out.println("Tarefa editada com sucesso!");
            Space();
        } else {
            System.out.println("Tarefa inexistente!");
            Space();
        }
    }

    public static void DeleteTask(String[][] matriz, int lines, String nomeTarefa) {
        boolean search = false;
        for (int i = 0; i < lines; i++) {
            if (matriz[i][0] != null && matriz[i][0].equals("Nome: " + nomeTarefa)) {
                search = true;
                matriz[i][0] = null;
                matriz[i][1] = null;
                matriz[i][2] = null;
                matriz[i][3] = null;
                break;
            }
        }
        if (search) {
            System.out.println("A tarefa foi excluida com sucesso!");
            Space();
        } else {
            System.out.println("Tarefa inexistenete!");
            Space();
        }

    }

    public static boolean DateVerify(String data) {
        String regex = "^([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/([0-9][0-9])?[0-9][0-9]$";
        return Pattern.matches(regex, data);
    }

    public static void DataInsert(String[][] matriz, String data, int i) {
        Scanner scanner = new Scanner(System.in);
        boolean verify;

        verify = DateVerify(data);

        while (!verify) {
            System.out.println("Formato de data inválida, insira no formato dd/mm/yy");
            data = scanner.next();
            verify = DateVerify(data);
            Space();
        }
        matriz[i][2] = "Data: " + data;
    }

    public static void StatusController(String[][] matriz, String taskName, int lines) {
        Scanner scanner = new Scanner(System.in);
        boolean result = false;
        int option;
        for (int i = 0; i < lines; i++) {
            if (matriz[i][0] != null && matriz[i][0].equals("Nome: " + taskName)) {
                result = true;
                switch (matriz[i][3]) {
                    case "Status: A Fazer":
                        do {
                            System.out.println("Escolha o Status da nova tarefa:");
                            System.out.println("1 - Fazendo");
                            System.out.println("2 - Completa");
                            option = scanner.nextInt();
                            Space();
                        } while (option < 1 || option > 2);
                        if (option == 1) {
                            matriz[i][3] = "Status: Fazendo";
                        } else {
                            matriz[i][3] = "Status: Completa";
                        }
                        break;

                    case "Status: Fazendo":
                        do {
                            System.out.println("Escolha o Status da nova tarefa:");
                            System.out.println("1 - A Fazer");
                            System.out.println("2 - Completa");
                            option = scanner.nextInt();
                            Space();
                        } while (option < 1 || option > 2);
                        if (option == 1) {
                            matriz[i][3] = "Status: A Fazer";
                        } else {
                            matriz[i][3] = "Status: Completa";
                        }
                        break;

                    case "Status: Completa":
                        do {
                            System.out.println("Escolha o Status da nova tarefa:");
                            System.out.println("1 - A fazer");
                            System.out.println("2 - Fazendo");
                            option = scanner.nextInt();
                            Space();
                        } while (option < 1 || option > 2);
                        if (option == 1) {
                            matriz[i][3] = "Status: A Fazer";
                        } else {
                            matriz[i][3] = "Status: Fazendo";
                        }
                        break;
                }
                break;
            }
        }
        if (result) {
            System.out.println("Tarefa editada com sucesso!");
        }
        else {
            System.out.println("Tarefa inexistente!");
        }
    }

    public static void PrintTaskList() {
        System.out.println("LISTA DE TAREFAS");
    }

    public static void PrintTaskListOptions () {
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Criar nova tarefa");
        System.out.println("2 - Listar todas as tarefas");
        System.out.println("3 - Editar tarefa");
        System.out.println("4 - Alterar status");
        System.out.println("5 - Excluir tarefa");
        System.out.println("6 - Sair");
        Space();
    }
    public static void Space() {
        System.out.println();
    }

    public static void main(String[] args) {

        String[][] lista;
        int linhas, opcao;
        int colunas = 4;
        Scanner scanner = new Scanner(System.in);
        String nomeTarefa;
        do {
            System.out.println("Insira o tamanho de sua lista de tarefas: ");
            linhas = scanner.nextInt();
            Space();
        } while (linhas <= 0);

        lista = new String[linhas][colunas];
        do {
            PrintTaskList();
            PrintTaskListOptions();
            opcao = scanner.nextInt();
            while (opcao < 1 || opcao > 6) {
                System.out.println("Opção inválida!");
                PrintTaskList();
                PrintTaskListOptions();
                opcao = scanner.nextInt();
                Space();
            }
            Space();
            switch (opcao) {
                case 1:
                    CreateNewTask(lista, linhas, colunas);
                    Space();
                    break;

                case 2:
                    PrintTaskList();
                    ShowTasks(lista, linhas, colunas);
                    Space();
                    break;

                case 3:
                    System.out.println("Insira o nome da tarefa que deseja editar:");
                    nomeTarefa = scanner.next();
                    EditTask(lista, linhas, nomeTarefa);
                    Space();
                    break;

                case 4:
                    System.out.println("Insira o nome da tarefa que deseja alterar o status:");
                    nomeTarefa = scanner.next();
                    StatusController(lista, nomeTarefa, linhas);
                    Space();
                    break;

                case 5:
                    System.out.println("Insira o nome da tarefa que deseja excluir:");
                    nomeTarefa = scanner.next();
                    DeleteTask(lista, linhas, nomeTarefa);
                    Space();
                    break;

                case 6:
                    System.out.println("Encerrando...");
                    Space();
                    break;

                default:
                    System.out.println("Opção Inválida!");
                    Space();

            }
        } while (opcao != 6);

    }
}

