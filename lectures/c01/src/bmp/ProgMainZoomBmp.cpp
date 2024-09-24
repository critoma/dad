// g++ -o zoomInBmp.elf64 ProgMainZoomBmp.cpp
// hexdump -C input_square.bmp
// ./zoomInBmp.elf64 2 input_square.bmp output_square.bmp
// hexdump -C output_square.bmp
// ./zoomInBmp.elf64 10 input_snail.bmp output_snail.bmp
// rm -rf output_*.bmp *.elf64

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define _BFHSIZE_ 14 
#define _BIHSIZE_ 40 
#define packed_data __attribute__((__packed__)) 

struct packed_data BitmapFileHeader {
    char bfType[2];		//intotdeauna setat pe 'BM', adik .bmp file
    int bfSize;		//specifica dimensiunea fisierului in bytes
    char bfReserved1[2];	//intotdeauna setat pe 0
    char bfReserved2[2];	//      --,,--
    char bfOffBits[4];		//deplasament de unde incep bitii de codificare poza
				//poza se stocheaza invers cu padding valoarea 0 
				//pana da numarul de octeti pe linie multiplu de 4
}; //all 14 bytes data structure

struct packed_data BitmapInfoHeader {
    int biSize;		//specifica dimensiunea info header-ului in bytes
    int biWidth;		//specifica latimea imaginii in pixeli
    int biHeight;		//specifica inaltimea imaginii in pixeli
    char biPlanes[2];		//set to 0
    short int biBitCount;	//specifina numarul de biti per pixel
    char biCompression[4];  	//tip compresie->0    
    int biSizeImage;	//specifica dimensiunea imaginii in bytes. 
                            	//no compression=>0
    char biXPelsPerMeter[4]; 	//usually 0
    char biYPelsPermeter[4]; 	//--,,--    
    char biClrUsed[4];
    char biClrImportant[4];
}; // 40 bytes

int main (int argc, char* argv[])
{
	FILE *fSrc;	FILE *fDst;

	BitmapFileHeader bfhSrc, bfhDst;
	BitmapInfoHeader bihSrc, bihDst;
		
	int buffer = 0;
	int i = 0, j = 0, k = 0, n = 0; int zoom = 0;
	
	int widthVechi = 0;	int heightVechi = 0;
	int widthNou = 0; int heightNou = 0;

	char numeFis[100]; char numeFisNou[100];
	char optZoom[3];

	if ( argc != 4 )
		printf("\nWrong number of parameters. Sample: ZooomBMP.exe 2 f1.bmp f2.bmp");
	else {
		strcpy(optZoom, argv[1]);
		zoom = atoi(optZoom);
		strcpy(numeFis, argv[2]); strcpy(numeFisNou, argv[3]);
		fSrc = fopen(numeFis, "rb");


		fread(&bfhSrc, _BFHSIZE_, 1, fSrc); // it is multilpe of 8 in memory aligniament
		fread(&bihSrc, _BIHSIZE_, 1, fSrc);

		printf("\n Dim bfh: %ld, dim bih: %ld, biBitCount: %d", sizeof(BitmapFileHeader), sizeof(BitmapInfoHeader), bihSrc.biBitCount);
		

		if ((strncmp(bfhSrc.bfType, "BM", 2) == 0) && (bihSrc.biSize == 40) && (bihSrc.biBitCount == 24)) {
		//if ( (strncmp(bfhSrc.bfType, "BM", 2) == 0) ) {
            printf("\n Let's fun begin with zoom in! %d \n", bihSrc.biSize);
			fDst = fopen(numeFisNou, "wb+");
			bfhDst = bfhSrc;
			bihDst = bihSrc;

			bihDst.biWidth = bihSrc.biWidth * zoom;
			bihDst.biHeight = bihSrc.biHeight * zoom;
			long int padW = (bihDst.biWidth * ((bihDst.biBitCount)/8));
			padW = padW + (padW % 12); //12 = 3 bytes/pixel x 4 (the multiple value mandatory per line)
			bfhDst.bfSize = bihDst.biHeight * padW;

			fwrite(&bfhDst, _BFHSIZE_, 1, fDst);
			fwrite(&bihDst, _BIHSIZE_, 1, fDst);

			//COPIEZ SI MULTIPLIC B DIN VECHIUL IN NOUL FISIER DE ZOOM ORI
			for (i = 0; i < bihSrc.biHeight; i++)
			{
				//it is multiplied one line from height
				long int relPos = ftell(fSrc);
				for (j = 0; j < bihSrc.biWidth; j++)
				{
					buffer = 0x00000000;
					fread(&buffer, sizeof(char), 3, fSrc);
					//multiplic de zoom ori B de pe o linie
					for(k = 0; k < zoom; k++)
						fwrite(&buffer, sizeof(char), 3, fDst);
				}

				//multiplic linia respectiva de zoom-1 ori in noul fisier
				for(int n = 0; n < zoom-1; n++)
				{
					//ma plasez la inceputul aceleiasi linii din vechiul fisier
					//fseek(fSrc, -3*widthVechi, SEEK_CUR);
					fseek(fSrc, relPos, SEEK_SET);
					//citesc iarasi linia si o multiplic
					for (j=0; j < bihSrc.biWidth; j++)
					{
						buffer = 0x00000000;
						fread(&buffer, sizeof(char), 3, fSrc);
						for(k = 0; k < zoom; k++)
							fwrite(&buffer, sizeof(char), 3, fDst);
					}
				}
			}

			fclose(fDst);			
		} else {
			printf("\n The file seems to not be an BMP with 24 bits code per pixel");
			return 1;			
		}
	
		fclose(fSrc);
	}
	return 0;//everything is ok
}
