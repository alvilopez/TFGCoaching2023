import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { fabric } from 'fabric';
import html2canvas from 'html2canvas';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-photo-editor',
  templateUrl: './photo-editor.component.html',
  styleUrls: ['./photo-editor.component.css']
})
export class PhotoEditorComponent implements OnInit {

  @ViewChild('imageElement', { static: true }) imageElement!: ElementRef;

  canvas!: HTMLCanvasElement;
  context!: CanvasRenderingContext2D;
  drawing = false;
  @Input()
  imageUrl!: string;
  strokeColor = '#aaaaaa';

  @ViewChild('canvas') canvasRef!: ElementRef;

  ngOnInit() {
     // Reemplaza esto con la URL de la imagen que desees cargar
  }

  ngAfterViewInit() {
    const canvas = this.canvasRef.nativeElement;
    const context = canvas.getContext('2d');

    const imagen = new Image();
    imagen.src = this.imageUrl; // Ruta de la foto en assets

    imagen.onload = () => {
      canvas.width = imagen.width;   // Establece el ancho del canvas igual al ancho de la imagen
      canvas.height = imagen.height;
      context.drawImage(imagen, 0, 0);
      // Aquí puedes realizar cualquier operación adicional en el canvas
    };

  }

  startDrawing(event: MouseEvent) {
    this.drawing = true;
    const x = event.offsetX;
    const y = event.offsetY;
    this.context.beginPath();
    this.context.moveTo(x, y);
  }

  draw(event: MouseEvent) {
    if (!this.drawing) return;
    const x = event.offsetX;
    const y = event.offsetY;
    this.context.lineTo(x, y);
    this.context.strokeStyle = this.strokeColor;
    this.context.stroke();
  }

  endDrawing() {
    this.drawing = false;
  }

  downloadImage() {
    html2canvas(this.canvas).then((canvas) => {
      canvas.toBlob((blob) => {
        if(blob!=null)
        saveAs(blob, 'drawing.png');
      });
    });
  }

  shareOnSocialMedia() {
    html2canvas(this.canvas).then((canvas) => {
      const base64Image = canvas.toDataURL('image/png');
      // Aquí puedes implementar la lógica para compartir en redes sociales
      console.log('Compartir en redes sociales:', base64Image);
    });
  }

}
