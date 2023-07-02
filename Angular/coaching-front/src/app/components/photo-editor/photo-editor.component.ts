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

  @ViewChild('imageElement', { static: true }) imageElement!: ElementRef;
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
    const image: HTMLImageElement = this.imageElement.nativeElement;

    const combinedCanvas = document.createElement('canvas');
    combinedCanvas.width = canvas.width;
    combinedCanvas.height = canvas.height;

    const combinedContext = combinedCanvas.getContext('2d') ;
    combinedContext?.drawImage(image, 0, 0);
    combinedContext?.drawImage(canvas, 0, 0);

    combinedContext?.canvas.toDataURL('image.png');

    const imageDataURL = combinedCanvas.toDataURL('image/png');

  // Hacer algo con la cadena base64, como mostrarla en una imagen
  const imgElement = document.createElement('img');
  imgElement.src = imageDataURL;
  document.body.appendChild(imgElement);


  }



}




// downloadImage() {
//   html2canvas(this.canvas).then((canvas) => {
//     canvas.toBlob((blob) => {
//       if(blob!=null)
//       saveAs(blob, 'drawing.png');
//     });
//   });
// }

// shareOnSocialMedia() {
//   html2canvas(this.canvas).then((canvas) => {
//     const base64Image = canvas.toDataURL('image/png');
//     // Aquí puedes implementar la lógica para compartir en redes sociales
//     console.log('Compartir en redes sociales:', base64Image);
//   });
// }
