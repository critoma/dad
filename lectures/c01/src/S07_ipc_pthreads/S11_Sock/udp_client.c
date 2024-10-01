// gcc -o udp_client.elf udp_client.c
// ./udp_client.elf 127.0.0.1 2000 

#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>

#include <stdlib.h> //atoi
#include <ctype.h> // toupper
#include <fcntl.h> // for open
#include <unistd.h> // for close

int main(int argc, char** argv){
    int socket_desc;
    struct sockaddr_in server_addr;
    char server_message[100], client_message[100];
    unsigned int server_struct_length = sizeof(server_addr);

    if(argc != 3)
    {
        printf("\n UDP Usage: %s <ip of server> <port> \n", argv[0]);
        return 1;
    }
    
    // Clean buffers:
    memset(server_message, '\0', sizeof(server_message));
    memset(client_message, '\0', sizeof(client_message));
    
    // Create socket:
    socket_desc = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);
    
    if(socket_desc < 0){
        printf("Error while creating socket\n");
        return -1;
    }
    printf("Socket created successfully\n");
    
    // Set port and IP:
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(atoi(argv[2]));
    server_addr.sin_addr.s_addr = inet_addr(argv[1]);
    
    // Get input from the user:
    printf("Enter message: ");
    if (fgets(client_message, sizeof(client_message), stdin) != 0)
    {
        // Send the message to server:
        if(sendto(socket_desc, client_message, strlen(client_message), 0,
            (struct sockaddr*)&server_addr, server_struct_length) < 0){
            printf("Unable to send message\n");
            return -1;
        }
        
        // Receive the server's response:
        if(recvfrom(socket_desc, server_message, sizeof(server_message), 0,
            (struct sockaddr*)&server_addr, &server_struct_length) < 0){
            printf("Error while receiving server's msg\n");
            return -1;
        }
        
        printf("Server's response: %s\n", server_message);
        
        // Close the socket:
        close(socket_desc);
    } // end while
    
    return 0;
}
