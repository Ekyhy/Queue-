import java.util.Scanner;
import java.net.URL;
import javazoom.jl.player.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class Node {
    String data;
    Node next;
    Node(String new_data) {
        data = new_data;
        next = null;
    }
}

class ejaNomor {
    public String ejaNomor(String nomor){
    StringBuilder hasil = new StringBuilder();

    for (char c : nomor.toCharArray()) {
            switch (c) {
            case '0': hasil.append("nol "); break;
            case '1': hasil.append("satu "); break;
            case '2': hasil.append("dua "); break;
            case '3': hasil.append("tiga "); break;
            case '4': hasil.append("empat "); break;
            case '5': hasil.append("lima "); break;
            case '6': hasil.append("enam "); break;
            case '7': hasil.append("tujuh "); break;
            case '8': hasil.append("delapan "); break;
            case '9': hasil.append("sembilan "); break;
            default: hasil.append(c).append(" ");
        }
    }
    return hasil.toString();
    }
}

class IndoTTS {
    public void speak(String text) {
        try {
            String urlStr = "https://translate.google.com/translate_tts?ie=UTF-8&tl=id&client=tw-ob&q="
                    + text.replace(" ", "%20");

            URL url = new URL(urlStr);
            Player player = new Player(url.openStream());
            player.play();

        } catch (Exception e) {
            System.out.println("Error TTS: " + e.getMessage());
        }
    }
}

class myQueue {
    private Node depan;
    private Node belakang;
    private int nomorAntrian;
    private int currSize;
    private IndoTTS tts = new IndoTTS();

    public myQueue(){
        currSize = 0;
        depan = belakang = null;
    }

    public boolean isEmpty(){
        return depan == null;
    }

    public void enqueue(String new_data){
        String nomor = String.format("A    %03d" , 1 + nomorAntrian++);
        String mixData = nomor + " - " + new_data;

        Node new_node = new Node(mixData);
        if (isEmpty()) {
            depan = belakang = new_node;
        } else {
            belakang.next = new_node;
            belakang = new_node;
        }

        currSize++;
    }

    public String dequeue(){
        if (isEmpty()){
            tts.speak("Antrian Kosong! Isi antrian kembali!");
            return "Antrian Kosong!";
        }

        String hapusData = depan.data;
        depan = depan.next;
        if(depan == null)belakang = null;

        currSize--;

        ejaNomor eja = new ejaNomor();

        String[] parts = hapusData.split(" - ");
        String nomor = parts[0];
        String nama = parts[1];

        String nomorEja = eja.ejaNomor(nomor);
        tts.speak("Nomor antrian " + nomorEja + " atas nama " + nama + ", silakan ke loket");
        return hapusData;
    }

    public String getDepan(){
        if (isEmpty()){
            return "Antrian Kosong!";
        }
        return depan.data;
    }

    public String getBelakang(){
        if (isEmpty()) {
            return "Antrian Kosong!";
        }
        return belakang.data;
    }

    public void  showData(){
        if (isEmpty()) {
            System.out.println("Antrian Kosong!");
            return;
        }
        Node temp = depan;
        System.out.print("Isi Antrian: ");
        
        while (temp !=  null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
    }

    public Node getFirstNode(){
        return depan;
    }

    public int size(){
        return currSize;
    }
}

public class QueueGUI extends JFrame {

    private myQueue queue = new myQueue();

    private JTextField inputNama;
    private JTextArea areaAntrian;
    private JLabel labelSize;

    public QueueGUI() {
        setTitle("Sistem Antrian Loket");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // ===== TOP (INPUT) =====
        JPanel topPanel = new JPanel();
        inputNama = new JTextField(20);
        JButton btnTambah = new JButton("Tambah");

        topPanel.add(new JLabel("Nama: "));
        topPanel.add(inputNama);
        topPanel.add(btnTambah);

        // ===== CENTER (DISPLAY) =====
        areaAntrian = new JTextArea();
        areaAntrian.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaAntrian);

        // ===== BOTTOM (BUTTONS) =====
        JPanel bottomPanel = new JPanel();

        JButton btnHapus = new JButton("Panggil");
        JButton btnRefresh = new JButton("Refresh");
        labelSize = new JLabel("Jumlah: 0");

        bottomPanel.add(btnHapus);
        bottomPanel.add(btnRefresh);
        bottomPanel.add(labelSize);

        // ===== ADD KE FRAME =====
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        // ===== EVENT HANDLER =====
        btnTambah.addActionListener(e -> tambahData());
        btnHapus.addActionListener(e -> hapusData());
        btnRefresh.addActionListener(e -> tampilkanData());
    }

    private void tambahData() {
        String nama = inputNama.getText();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!");
            return;
        }

        queue.enqueue(nama);
        inputNama.setText("");
        tampilkanData();
    }

    private void hapusData() {
        String data = queue.dequeue();
        JOptionPane.showMessageDialog(this, "Dipanggil: " + data);
        tampilkanData();
    }

    private void tampilkanData() {
        areaAntrian.setText("");

        if (queue.isEmpty()) {
            areaAntrian.setText("Antrian Kosong!");
        } else {
            Node temp = queue.getFirstNode(); 
            while (temp != null) {
                areaAntrian.append(temp.data + "\n");
                temp = temp.next;
            }
        }

        labelSize.setText("Jumlah: " + queue.size());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QueueGUI().setVisible(true));
    }
}