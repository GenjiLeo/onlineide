import { Component, OnInit } from '@angular/core';
import { SourceFile } from '../classes/sourceFile';
import { ActivatedRoute } from "@angular/router";
import { HttpClient } from '@angular/common/http';
import * as globalVars from '../globals';
import { Project } from '../classes/project';

@Component({
  selector: 'app-project-view',
  templateUrl: './project-view.component.html',
  styleUrls: ['./project-view.component.css']
})
export class ProjectViewComponent implements OnInit {

  projectId: string;
  projectName: string;
  projectContributors: string[];

  sourceFiles: SourceFile[];
  // Used to change the syntax highlighting in the code editor appropriately
  languageByExtension = {
    "java": "java",
    "c": "c",
  }


  constructor (private route: ActivatedRoute, private http: HttpClient) {
    this.route.params.subscribe( params => {
      console.log(params);
      this.projectId = params.id;
      // Get project name
      this.updateProjectDetails();
      // Get files for the project
      this.updateProjectFiles();
    });
  }

  // Update project name / project contributors
  updateProjectDetails() {
    this.http.get(globalVars.baseURL + '/project/findById/' + this.projectId, globalVars.httpOptions).subscribe((retVal: Project) => {
      this.projectName = retVal.name;
      this.projectContributors = retVal.contributers;
      console.log("Contributors: " + this.projectContributors);
    });
  }

  // Update sourceFile list for the current project from the backend
  updateProjectFiles() {
    this.http.get(globalVars.baseURL + '/project/files/' + this.projectId, globalVars.httpOptions).subscribe((retVal: SourceFile[]) => {
      console.log(retVal);
      this.sourceFiles = retVal;
      if(this.sourceFile.id != "") {
        // Go through the list and find the currently open file
        // to make sure that we're working with the current instance of the file
        var newFile = this.sourceFiles.find(elem => elem.id == this.sourceFile.id);
        if (newFile != undefined) {
          this.showFile(newFile);
        } else {
          this.disableEditor();
        }
      }
    });
  }

  editorDisabled = true;
  compileDisabled = true;

  editorOptions = {theme: 'vs-light', language: 'plaintext', readOnly: true};

  defaultFile: SourceFile = {
    id: "",
    name: "",
    content: "",
    project: {
      id: ""
    }
  }

  // Default output at the bottom
  compilerOutput = "Not compiled yet";

  updateCompilerOutput(output: string) {
    this.compilerOutput = output
  }

  // Set default file
  sourceFile = this.defaultFile;

  // Switch to this file and enable the editor
  showFile(sf: SourceFile) {
    this.editorDisabled = false;
    this.sourceFile = sf;
    var fileExtension = sf.name.split('.').pop();
    // Language is plaintext unless the extension is .java or .c
    // Used to enable syntax highlighting
    var language = "plaintext";
    if (fileExtension in this.languageByExtension) {
      language = this.languageByExtension[fileExtension];
    }
    // We have to update the options object like this so that the editor
    // gets notified of these changes
    this.editorOptions = { ...this.editorOptions, language: language, readOnly: false };
  }

  disableEditor() {
    this.editorDisabled = true;
    this.compileDisabled = true;
    this.sourceFile = this.defaultFile;
    this.editorOptions = { ...this.editorOptions, readOnly: true };
  }

  // Frontend handling for the dark mode button, dark mode disabled on page load
  darkModeEnabled = false;

  checkDarkMode() {
    this.http.get(globalVars.baseURL + '/dark-mode').subscribe((retVal: boolean) => {
      if(retVal != this.darkModeEnabled) {
        if(retVal) {
          console.log("Enabling dark mode");
          this.editorOptions = { ...this.editorOptions, theme: 'vs-dark'};
        } else {
          console.log("Disabling dark mode");
          this.editorOptions = { ...this.editorOptions, theme: 'vs-light'};
        }
        this.darkModeEnabled = retVal;
      }
    });
  }

// Check dark mode in a given time interval
  ngOnInit(): void {
    this.checkDarkMode();
    setInterval(() => {
      this.checkDarkMode();
    }, 2000)
  }

}
