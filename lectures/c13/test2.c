#include <emscripten/emscripten.h>
#include <stdio.h>

int EMSCRIPTEN_KEEPALIVE hello(int argc, char ** argv) {
  printf("Hello 2!\n");
  return 8;
}

