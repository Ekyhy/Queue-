import tkinter as tk
from tkinter import messagebox
from gtts import gTTS
import pygame
import os

# ====== NODE ======
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None


# ====== EJA NOMOR ======
class EjaNomor:
    @staticmethod
    def eja(nomor):
        mapping = {
            '0': 'nol',
            '1': 'satu',
            '2': 'dua',
            '3': 'tiga',
            '4': 'empat',
            '5': 'lima',
            '6': 'enam',
            '7': 'tujuh',
            '8': 'delapan',
            '9': 'sembilan'
        }
        return " ".join([mapping.get(c,c) for c in nomor])


# ====== TEXT TO SPEECH ======
def speak(text):
    tts = gTTS(text=text, lang='id')
    tts.save("voice.mp3")

    pygame.mixer.init()
    pygame.mixer.music.load("voice.mp3")
    pygame.mixer.music.play()

    while pygame.mixer.music.get_busy():
        continue

    pygame.mixer.quit()
    os.remove("voice.mp3")



# ====== QUEUE ======
class MyQueue:
    def __init__(self):
        self.depan = None
        self.belakang = None
        self.currSize = 0
        self.nomorAntrian = 1

    def isEmpty(self):
        return self.depan is None

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

    def dequeue(self):
        if self.isEmpty():
            speak("Antrian kosong")
            return "Antrian Kosong!"

        hapusData = self.depan.data
        self.depan = self.depan.next

        if self.depan is None:
            self.belakang = None

        self.currSize -= 1
        return hapusData

    def showData(self):
        if self.isEmpty():
            print("Antrian Kosong!")
            return

        result = []
        temp = self.depan
        nomor = 1

        while temp:
            result.append(temp.data)
            temp = temp.next
            nomor += 1
        return result

    def size(self):
        return self.currSize


class App:
    def __init__(self, root):
        self.root = root
        self.root.title("Sistem Antrian Loket")
        self.root.geometry("500x400")

        self.queue = MyQueue()

        # INPUT
        self.input = tk.Entry(root, font=("Arial", 14))
        self.input.pack(pady=10)

        # BUTTONS
        tk.Button(root, text="Tambah Antrian", command=self.enqueue, bg="green", fg="white").pack(pady=5)
        tk.Button(root, text="Panggil Antrian", command=self.dequeue, bg="red", fg="white").pack(pady=5)
        tk.Button(root, text="Tampilkan", command=self.show_data).pack(pady=5)

        # OUTPUT
        self.output = tk.Text(root, height=10, font=("Arial", 12))
        self.output.pack(pady=10)

    def enqueue(self):
        nama = self.input.get()
        if not nama:
            messagebox.showwarning("Error", "Nama tidak boleh kosong")
            return

        data = self.queue.enqueue(nama)
        self.input.delete(0, tk.END)
        self.show_data()

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

    def show_data(self):
        self.output.delete("1.0", tk.END)
        data = self.queue.showData()

        if not data:
            self.output.insert(tk.END, "Antrian kosong")
            return

        for i, item in enumerate(data, 1):
            self.output.insert(tk.END, f"{i}. {item}\n")


# ===== RUN =====
root = tk.Tk()
app = App(root)
root.mainloop()