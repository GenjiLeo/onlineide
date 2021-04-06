import { Component, OnInit } from '@angular/core';
import { Project } from '../classes/project'
import { Router } from '@angular/router'
import {MatTableDataSource} from '@angular/material/table'
import {MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Inject } from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import * as globalVars from '../globals';
import {AuthService} from "../auth.service";

export interface DialogData {
  name: string;
}

@Injectable()
@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {

  projectName = "";

  projects: Project[] = [
  ]
  //Dynamic data source provides a quick way to re-render table
  projectsDataSource = new MatTableDataSource<Project>(this.projects);

  displayedColumns:string[] = ['name','actions']
  constructor(private router : Router, public dialog: MatDialog, private http: HttpClient, public authService:AuthService) { }

  updateProjectList() {
    this.http.get(globalVars.baseURL + '/project/findAll', globalVars.httpOptions).subscribe((retVal: Project[]) => {
      console.log(retVal);
      this.projectsDataSource.data = retVal;
    })
  }

  // Make this route to the specific project we just clicked
  selectedProject(project: Project) {
    this.router.navigate(['ide/' + project.id])
  }

  addProject() {
    var project = { name: this.projectName };
    // POST project name to backend, add ID and name from response to list
    // If we get a HTTP response code != 200 dump the error message to the user
    this.projectName = "" // clear text field
    console.log(this.projects)

    this.http.post(globalVars.baseURL + '/project', project, globalVars.httpOptions).subscribe((retVal) => {
      console.log(retVal);
      this.updateProjectList();
    });
  }

  editProject(project) {
    this.openEditProjectDialog(project);
    console.log(project);
  }

  deleteProject(project) {
    console.log("Deleting project " + project);
    this.http.request('delete', globalVars.baseURL + '/project', {body: project}).subscribe((retVal) => {
      console.log(retVal);
      this.updateProjectList();
    });
  }

  openEditProjectDialog(project) {
    const dialogRef = this.dialog.open(EditProjectNameDialog, {
      width: '250px',
      data: {name: project.name}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result && result != project.name) {
        console.log('Project was renamed to ' + result);
        project.name = result;
        this.http.put(globalVars.baseURL + '/project', project).subscribe((retVal) => {
          console.log(retVal);
          this.updateProjectList();
        });
      } else {
        console.log('Project was not renamed');
      }
    });
  }

  ngOnInit(): void {
    this.updateProjectList();
  }

}

// Component for editing the project name
@Component({
  selector: 'project-list.edit-name-dialog.component',
  templateUrl: 'project-list.edit-name-dialog.component.html',
})
export class EditProjectNameDialog {

  constructor(
    public dialogRef: MatDialogRef<EditProjectNameDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

}