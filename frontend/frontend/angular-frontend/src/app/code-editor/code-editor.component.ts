import { Component, OnInit } from '@angular/core';
import { Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SourceFile } from '../classes/sourceFile';
import { ProjectViewComponent } from '../project-view/project-view.component';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as globalVars from '../globals';
import { CompilerReturnValue } from '../classes/compilerReturnValue';

export interface DialogData {
  name: string;
}

@Injectable()
@Component({
  selector: 'app-code-editor',
  templateUrl: './code-editor.component.html',
  styleUrls: ['./code-editor.component.css']
})
export class CodeEditorComponent implements OnInit {

  // Inject the parent component so that we can access its attributes
  constructor(public dialog: MatDialog, @Inject(ProjectViewComponent) public parent: ProjectViewComponent, private http: HttpClient) { }

  saveFile() {
    this.parent.compileDisabled = false;
    this.http.put(globalVars.baseURL + '/project/sourceFile', this.parent.sourceFile, globalVars.httpOptions).subscribe((retVal) => {
      console.log(retVal);
    });
  }


  compileFile() {
    this.http.post(globalVars.compileBaseURL + '/compile', this.parent.sourceFile, globalVars.httpOptions).subscribe((retVal: CompilerReturnValue) => {
      console.log(retVal);
      if(retVal.compilable) {
        if(retVal.stderr != "") {
          this.parent.updateCompilerOutput("Successfully compiled\nCompiler Output:\n\n" + retVal.stderr);
        } else {
          this.parent.updateCompilerOutput("Successfully compiled");
        }
      } else {
        this.parent.updateCompilerOutput(retVal.stderr);
      }
    });
  } 

  deleteFile() {
    console.log("Deleting project " + this.parent.sourceFile);
    this.http.request('delete', globalVars.baseURL + '/project/sourceFile', {body: this.parent.sourceFile}).subscribe((retVal) => {
      console.log(retVal);
      this.parent.updateProjectFiles();
      this.parent.disableEditor();
    });
  }

  // Disable compile button until file has been saved (see specification)
  codeChanged() {
    this.parent.compileDisabled = true;
  }

  // Opens the dialog where you can change the file name
  openEditFileNameDialog() {
    const dialogRef = this.dialog.open(EditFileNameDialog, {
      width: '250px',
      data: {name: this.parent.sourceFile.name}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result != this.parent.sourceFile.name) {
        this.parent.sourceFile.name = result;
        console.log(this.parent.sourceFile);
        this.http.put(globalVars.baseURL + '/project/sourceFile', this.parent.sourceFile, globalVars.httpOptions).subscribe((retVal) => {
          console.log(retVal);
          this.parent.updateProjectFiles();
        });
        console.log('Sourcefile was renamed to ' + result);
      } else {
        console.log('Sourcefile was not renamed');
      }
    });
  }

  ngOnInit(): void {
  }

}

// Define dialog for file name change
@Component({
  selector: 'code-editor-name-dialog.component',
  templateUrl: 'code-editor.edit-name-dialog.component.html',
})
export class EditFileNameDialog {

  constructor(
    public dialogRef: MatDialogRef<EditFileNameDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

}