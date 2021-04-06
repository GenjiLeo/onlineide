#include <sys/socket.h> /* struct sockaddr, socket, bind, listen, accept, AF_INET, SOCK_STREAM */
#include <netinet/in.h> /* struct sockaddr_in, IPPROTO_TCP, INADDR_ANY  */
#include <string.h> /* strlen */
#include <unistd.h> /* write, sleep, close */

int main(int argc, char *argv[]) {
    /* initialize socket (file) handles and constants */
    int listen_fd = 0, conn_fd = 0, server_port = 5000;
    char response_buffer[128] = "HTTP/1.1 200 OK\n"
        "Content-Length: 12\n"
        "Content-Type: text/html\n"
        "\n"
        "Hello World!";
    struct sockaddr_in server_addr;

    /* Clear structure */
    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY); // converts unsigned int to network byte order
    server_addr.sin_port = htons(server_port); // converts unsigned short int to network byte order

    // TODO: implement web server
}

