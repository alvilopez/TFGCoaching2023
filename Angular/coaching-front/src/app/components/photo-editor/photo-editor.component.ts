import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { fabric } from 'fabric';
import html2canvas from 'html2canvas';
import { saveAs } from 'file-saver';
import { AnalysisService } from '../analysis/analysis.service';

@Component({
  selector: 'app-photo-editor',
  templateUrl: './photo-editor.component.html',
  styleUrls: ['./photo-editor.component.css']
})
export class PhotoEditorComponent implements OnInit {

  @Output()
  cerrarPopUp = new EventEmitter<boolean>();

  @ViewChild('canvas', { static: true }) canvas!: ElementRef;

  drawing = false;
  @Input() imageUrl!: string;
  strokeColor = '#000000';

  constructor(private analysisService : AnalysisService){}

  ngOnInit() {
    // Reemplaza esto con la URL de la imagen que desees cargar
  }

  ngAfterViewInit() {
    const canvas: HTMLCanvasElement = this.canvas.nativeElement;
    const context: CanvasRenderingContext2D | null = canvas.getContext('2d');

    const image = new Image();
    image.src = this.imageUrl; // Ruta de la foto en assets

    image.onload = () => {
      canvas.width = image.width;   // Establece el ancho del canvas igual al ancho de la imagen
      canvas.height = image.height;
      context?.drawImage(image, 0, 0);
    };
  }

  startDrawing(event: MouseEvent) {
    this.drawing = true;
    const canvas: HTMLCanvasElement = this.canvas.nativeElement;
    const context: CanvasRenderingContext2D | null = canvas.getContext('2d');

    const x = event.offsetX;
    const y = event.offsetY;
    context?.beginPath();
    context?.moveTo(x, y);
  }

  draw(event: MouseEvent) {
    if (!this.drawing) return;
    const canvas: HTMLCanvasElement = this.canvas.nativeElement;
    const context: CanvasRenderingContext2D | null = canvas.getContext('2d');

    const x = event.offsetX;
    const y = event.offsetY;
    context?.lineTo(x, y);
    if(context != null){
      context.strokeStyle = this.strokeColor;
      context.lineWidth = 5; // Ancho del trazo
    }

    context?.stroke();
  }

  endDrawing() {
    this.drawing = false;
  }

  generateCombinedImage() {
    const canvas: HTMLCanvasElement = this.canvas.nativeElement;
    const context: CanvasRenderingContext2D | null = canvas.getContext('2d');

    const combinedCanvas = document.createElement('canvas');
    combinedCanvas.width = canvas.width;
    combinedCanvas.height = canvas.height;

    const combinedContext = combinedCanvas.getContext('2d');
    combinedContext?.drawImage(canvas, 0, 0);

    const imageDataURL = combinedCanvas.toDataURL('image/jpeg'); // Cambiar a 'image/jpeg'

    // Descargar la imagen combinada como archivo JPG
    const blob = this.dataURItoBlob(imageDataURL);
    saveAs(blob, 'combined_image.jpg');
  }

  dataURItoBlob(dataURI: string): Blob {
    const byteString = atob(dataURI.split(',')[1]);
    const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);

    for (let i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }

    return new Blob([ab], { type: mimeString });
  }

}






// shareOnSocialMedia() {
//   html2canvas(this.canvas).then((canvas) => {
//     const base64Image = canvas.toDataURL('image/png');
//     // Aquí puedes implementar la lógica para compartir en redes sociales
//     console.log('Compartir en redes sociales:', base64Image);
//   });
// }
