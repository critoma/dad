# https://flaviocopes.com/webassembly/
# https://webassembly.org/getting-started/developers-guide/
# https://developer.mozilla.org/en-US/docs/WebAssembly/C_to_wasm


# cd /opt/software/emsdk/
cd /opt/software/
sudo ./emsdk install latest
./emsdk activate latest


cd /home/stud/dad/lectures/c11

/opt/software/emsdk/emscripten/1.38.31/
emcc test.c -s WASM=1 -o test.html

/opt/software/emsdk/emscripten/1.38.31/
emrun --no_browser --port 8080 .

http://127.0.0.1:8080/test.html



/opt/software/emsdk/emscripten/1.38.31/
emcc test2.c -s WASM=1 -o test2.html -s "EXTRA_EXPORTED_RUNTIME_METHODS=['ccall', 'cwrap']"

/opt/software/emsdk/emscripten/1.38.31/emrun --no_browser --port 8080 .

http://127.0.0.1:8080/test2.html


