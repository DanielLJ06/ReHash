import java.util.Random;

class ReHash {

    int vazio;
    int[] tabela;
    int ocupado;
    int total_colisoes;
    int[] ListaDeChaves;
    int[] ListaDeColisoes;

    public ReHash() {
        this.tabela = new int[100];
        this.vazio = -1;
        for (int i = 0; i < tabela.length; i++) {
            tabela[i] = vazio;
        }
        this.ocupado = 0;
        this.total_colisoes = 0;
        this.ListaDeChaves = new int[3];
        this.ListaDeColisoes = new int[3];
    }

    public int hashing(int chave) {
        return chave % tabela.length;
    }

    public int rehashing(int i) {
        return (i + 1) % tabela.length;
    }

    public int inserir_hashing(int chave) {
        if (ocupado >= tabela.length * 0.9) {
            return -1; 
        }

        int i = hashing(chave);
        int colisoes = 0;

        while (tabela[i] != vazio) {
            colisoes++;
            i = rehashing(i);
        }

        tabela[i] = chave;
        ocupado = ocupado + 1;
        total_colisoes += colisoes;

        for (int p = 0; p < 3; p++) {
            if (colisoes > ListaDeColisoes[p]) {
                for (int k = 2; k > p; k--) {
                    ListaDeColisoes[k] = ListaDeColisoes[k - 1];
                    ListaDeChaves[k] = ListaDeChaves[k - 1];
                }
                ListaDeColisoes[p] = colisoes;
                ListaDeChaves[p] = chave;
                break;
            }
        }

        return i;
    }
}

public class Main {
    public static void main(String[] args) {
        ReHash hash = new ReHash();
        Random random = new Random(1090);


        for (int n = 0; n < 100; n++) {
            hash.inserir_hashing(random.nextInt(100));
        }

      
        System.out.println("Total de colisões: " + hash.total_colisoes);


        System.out.println("Top 3 elementos que mais colidiram:");
        for (int p = 0; p < 3; p++) {
            System.out.println((p + 1) + "º: chave " + hash.ListaDeChaves[p]
                    + " -> " + hash.ListaDeColisoes[p] + " colisões");
        }
        System.out.print("Posições livres: ");
        for (int i = 0; i < hash.tabela.length; i++) {
            if (hash.tabela[i] == hash.vazio) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}
