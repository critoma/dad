// gcc -o udp_server.elf udp_server.c
// ./udp_server.elf 2000

#include <stdint.h>
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
    struct sockaddr_in server_addr, client_addr;
    char server_message[100], client_message[100];
    unsigned int client_struct_length = sizeof(client_addr);
    if (argc != 2)
        printf("\n UDP Usage: %s <port> \n", argv[0]);
    
    // Clean buffers:
    memset(server_message, '\0', sizeof(server_message));
    memset(client_message, '\0', sizeof(client_message));
    
    // Create UDP socket:
    socket_desc = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);
    
    if(socket_desc < 0){
        printf("Error while creating socket\n");
        return -1;
    }
    printf("Socket created successfully\n");
    
    // Set port and IP:
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(atoi(argv[1]));
    server_addr.sin_addr.s_addr = inet_addr("0.0.0.0");
    
    // Bind to the set port and IP:
    if(bind(socket_desc, (struct sockaddr*)&server_addr, sizeof(server_addr)) < 0){
        printf("Couldn't bind to the port\n");
        return -1;
    }
    printf("Done with binding\n");
    
    printf("Waiting for incoming messages...\n\n");
    
    // Receive client's message:
    if (recvfrom(socket_desc, client_message, sizeof(client_message), 0,
         (struct sockaddr*)&client_addr, &client_struct_length) < 0){
        printf("Couldn't receive\n");
        return -1;
    }
    printf("Received message from IP: %s and port: %i\n",
           inet_ntoa(client_addr.sin_addr), ntohs(client_addr.sin_port));
    
    printf("Msg from client: %s\n", client_message);
    
    // Change to uppercase:
    for(int i = 0; client_message[i]; ++i)
        client_message[i] = toupper(client_message[i]);
    
    // Respond to client:
    strcpy(server_message, client_message);
    
    if (sendto(socket_desc, server_message, strlen(server_message), 0,
         (struct sockaddr*)&client_addr, client_struct_length) < 0){
        printf("Can't send\n");
        return -1;
    }
    
    // Close the socket:
    close(socket_desc);
}
