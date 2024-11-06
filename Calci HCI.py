import tkinter as tk

# Initialize the main window
root = tk.Tk()
root.title("Simple Calculator")

# Entry widget to display the input/output
entry = tk.Entry(root, width=16, font=('Arial', 18), borderwidth=2, relief="solid", justify="right")
entry.grid(row=0, column=0, columnspan=4)

# Function to add characters to the entry widget
def click(character):
    current_text = entry.get()
    entry.delete(0, tk.END)
    entry.insert(tk.END, current_text + character)

# Function to clear the entry widget
def clear():
    entry.delete(0, tk.END)

# Function to evaluate the expression
def calculate():
    try:
        result = eval(entry.get())
        entry.delete(0, tk.END)
        entry.insert(tk.END, str(result))
    except Exception:
        entry.delete(0, tk.END)
        entry.insert(tk.END, "Error")

# Button layout
buttons = [
    ('7', 1, 0), ('8', 1, 1), ('9', 1, 2), ('/', 1, 3),
    ('4', 2, 0), ('5', 2, 1), ('6', 2, 2), ('*', 2, 3),
    ('1', 3, 0), ('2', 3, 1), ('3', 3, 2), ('-', 3, 3),
    ('0', 4, 0), ('.', 4, 1), ('+', 4, 2), ('=', 4, 3),
    ('C', 5, 0)
]

# Adding buttons to the window
for (text, row, col) in buttons:
    if text == '=':
        tk.Button(root, text=text, width=10, height=3, command=calculate).grid(row=row, column=col, columnspan=2)
    elif text == 'C':
        tk.Button(root, text=text, width=10, height=3, command=clear).grid(row=row, column=col, columnspan=4)
    else:
        tk.Button(root, text=text, width=5, height=3, command=lambda t=text: click(t)).grid(row=row, column=col)

# Start the main loop
root.mainloop()
