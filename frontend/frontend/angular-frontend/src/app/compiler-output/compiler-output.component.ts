import { Component, OnInit } from '@angular/core';
import { Inject } from '@angular/core';
import { ProjectViewComponent } from '../project-view/project-view.component';

@Component({
  selector: 'app-compiler-output',
  templateUrl: './compiler-output.component.html',
  styleUrls: ['./compiler-output.component.css']
})
export class CompilerOutputComponent implements OnInit {

  constructor(@Inject(ProjectViewComponent) public parent: ProjectViewComponent) { }

  ngOnInit(): void {
  }

}
