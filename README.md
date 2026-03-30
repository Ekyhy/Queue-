<h1> QUEUE / ANTRIAN </h1>
<h2>📖Pengertian</h2>
<p> </p>Queue disebut juga antrian merupakan struktur data yang mengikuti prinsip First-In-First-Out (FIFO) yang dapat diterjemahkan "element yang pertama masuk, elemen itu juga yang akan pertama keluar" </p>


<h6>♾️Tipe Data : Linked List</h6>
<p>struktur data linier dinamis yang terdiri dari serangkaian elemen (disebut node) yang saling terhubung menggunakan pointer.</p>
<li>Front : Pointer yang menunjukkan node pertama </li>
<li>Rear : Pointer yang menunjukkan node terakhir </li>

<h2>Operasi pada Queue</h2>
<h3>Enqueue</h3>
<h5>Java</h5>

```java

```
<h5>Python</h5>

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

<h3>Dequeue</h3>
<h5>Java</h5>

```java

```

<h5>Pyhton</h5>

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

