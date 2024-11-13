#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>

#define PIPE1 "/tmp/pipe1"
#define PIPE2 "/tmp/pipe2"
#define BUFFER_SIZE 1024

void count_word_character_line(char *text, int *word, int *character, int *line) {
    if (text == NULL || text[0] == '\0') {
        *character = *line = *word = 0;
        return;
    }
    
    *character = *line = *word = 0;
    int in_word = 0;
    
    for (int i = 0; text[i] != '\0'; i++) {
        (*character)++;
        
        if (text[i] == '\n') {
            (*line)++;
        }
        
        if (text[i] == ' ' || text[i] == '\n' || text[i] == '\t' || text[i] == '\r') {
            in_word = 0;
        }
        else if (in_word == 0) {
            in_word = 1;
            (*word)++;
        }
    }
}

void process1() {
    char input[BUFFER_SIZE], result[BUFFER_SIZE];
    
    int fd1 = open(PIPE1, O_WRONLY);
    if (fd1 < 0) {
        perror("Failed to open pipe for writing");
        exit(1);
    }
    
    printf("Enter a Sentence: ");
    fgets(input, BUFFER_SIZE, stdin);
    
    write(fd1, input, strlen(input) + 1);
    close(fd1);
    
    int fd2 = open(PIPE2, O_RDONLY);
    if (fd2 < 0) {
        perror("Failed to open pipe for reading");
        exit(1);
    }
    
    read(fd2, result, BUFFER_SIZE);
    printf("Result from Process 2:\n%s", result);
    close(fd2);
}

void process2() {
    char input[BUFFER_SIZE], result[BUFFER_SIZE];
    int chars, line, word;
    
    int fd1 = open(PIPE1, O_RDONLY);
    if (fd1 < 0) {
        perror("Failed to open pipe for reading");
        exit(1);
    }
    
    read(fd1, input, BUFFER_SIZE);
    close(fd1);
    
    count_word_character_line(input, &word, &chars, &line);
    snprintf(result, BUFFER_SIZE, "Characters: %d\nWords: %d\nLines: %d\n", chars, word, line);
    
    // Changed from PIPE1 to PIPE2
    int fd2 = open(PIPE2, O_WRONLY);
    if (fd2 < 0) {
        perror("Failed to open pipe for writing");
        exit(1);
    }
    
    write(fd2, result, strlen(result) + 1);
    close(fd2);
}

int main() {
    // Remove existing pipes if they exist
    unlink(PIPE1);
    unlink(PIPE2);
    
    // Create new pipes
    if (mkfifo(PIPE1, 0666) < 0) {
        perror("Failed to create pipe1");
        exit(1);
    }
    if (mkfifo(PIPE2, 0666) < 0) {
        perror("Failed to create pipe2");
        exit(1);
    }
    
    pid_t pid = fork();
    
    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    }
    else if (pid == 0) {
        process2();
    }
    else {
        process1();
    }
    
    // Clean up pipes
    unlink(PIPE1);
    unlink(PIPE2);
    
    return 0;
}