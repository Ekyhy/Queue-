# 🎫 Sistem Antrian (Queue) Berbasis Linked List

Repositori ini berisi implementasi struktur data **Queue (Antrian)** menggunakan tipe data **Linked List**. Dilengkapi dengan fitur pemanggilan suara (Text-to-Speech) untuk mensimulasikan sistem antrian dunia nyata (seperti di bank atau loket pelayanan). Tersedia dalam bahasa pemrograman **Java** dan **Python**.

---

## 📖 Pengertian

### 1. Queue (Antrian)
Queue adalah struktur data linier yang beroperasi berdasarkan prinsip **FIFO (First-In, First-Out)**. 
> *"Elemen yang pertama kali masuk ke dalam antrian, akan menjadi elemen pertama yang keluar dari antrian."*

### 2. Linked List
Dalam proyek ini, Queue dibangun menggunakan struktur **Linked List** dinamis. Linked list terdiri dari serangkaian elemen (disebut *node*) yang saling terhubung menggunakan *pointer* (penunjuk alamat memori).
* **Front (Depan)**: Pointer yang menunjukkan *node* pertama dalam antrian.
* **Rear (Belakang)**: Pointer yang menunjukkan *node* terakhir dalam antrian.

---

## ⚙️ Operasi Utama

### 1. Enqueue (Menambah Antrian)
Operasi untuk menambahkan data baru ke posisi paling belakang (*Rear*) dari antrian. Pada implementasi ini, sistem juga akan meng-generate nomor antrian secara otomatis (Contoh: `A 001`).

**Java**
```java
public void enqueue(String new_data) {
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
```

**Python**

```python
def enqueue(self, nama):
    nomor = f"A{self.nomorAntrian:03d}"
    dataGabung = f"{nomor} - {nama}"
    self.nomorAntrian += 1

    new_node = Node(dataGabung)

    if self.isEmpty():
        self.depan = self.belakang = new_node
    else:
        self.belakang.next = new_node
        self.belakang = new_node

    self.currSize += 1
    return dataGabung
```
### 2. Dequeue (Memanggil dan Menghapus Antrian)
Operasi untuk menghapus data dari posisi paling depan (Front). Pada sistem ini, dequeue juga memicu fitur Text-to-Speech (TTS) untuk mengeja nomor antrian dan memanggil nama secara otomatis.

**Java**

```java
public String dequeue() {
    if (isEmpty()) {
        tts.speak("Antrian Kosong! Isi antrian kembali!");
        return "Antrian Kosong!";
    }

    String hapusData = depan.data;
    depan = depan.next;
    if(depan == null) belakang = null;

    currSize--;

    ejaNomor eja = new ejaNomor();
    String[] parts = hapusData.split(" - ");
    String nomor = parts[0];
    String nama = parts[1];

    String nomorEja = eja.ejaNomor(nomor);
    tts.speak("Nomor antrian " + nomorEja + " atas nama " + nama + ", silakan ke loket");
    
    return hapusData;
}
```

**Python**

```python
def dequeue(self):
    data = self.queue.dequeue()

    if data is None:
        speak("Antrian kosong")
        messagebox.showinfo("Info", "Antrian kosong")
        return

    nomor, nama = data.split(" - ")
    nomor_eja = EjaNomor.eja(nomor)

    teks = f"Nomor antrian {nomor_eja} atas nama {nama}, silakan ke loket"
    speak(teks)

    self.show_data()
```

## 📚 Library & Dependensi Sound (TTS) ##

Untuk menjalankan fitur Text-to-Speech (Pemanggilan Suara), program ini membutuhkan library pihak ketiga. Pastikan Anda telah menginstalnya sebelum menjalankan program.

### Dependensi Java
Menggunakan JLayer untuk memutar file audio

```java
import javazoom.jl.player.Player;
```
<i>(Catatan: Harus dengan terlebih dahulu menambahkan file ```.jar``` JLayer ke dalam project structure/folder path yang digunakan) </i>

### Dependensi Python

Menggunakan gTTS (Google Text-to-Speech) untuk <i>generate</i> suara dan pygame untuk pemutarnya

```pthon
from gtts import gTTS
import pygame
```

**Cara Install (Terminal/CMD)**

```bash
pip install gTTS pygame
```



