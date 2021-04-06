import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Inject } from '@angular/core';
import { ProjectViewComponent } from '../project-view/project-view.component';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SourceFile } from '../classes/sourceFile';
import * as globalVars from '../globals';
import { AuthService } from '../auth.service';


export interface DialogData {
  name: string;
}

export interface ShareDialogData {
  name: string;
  contributors: string[];
}

export interface SharingResultDialogData {
  text: string;
  icon: string;
}

@Injectable()
@Component({
  selector: 'app-navigation-drawer',
  templateUrl: './navigation-drawer.component.html',
  styleUrls: ['./navigation-drawer.component.css']
})
export class NavigationDrawerComponent implements OnInit {

  constructor(
    private router: Router,
    @Inject(ProjectViewComponent) public parent: ProjectViewComponent,
    private http: HttpClient,
    public dialog: MatDialog,
    public authService: AuthService) { }
  imports: [
  ];

  showFile(file) {
    // Pass to parent because calling the parent function directly seems to break the scoping of variables
    this.parent.showFile(file);
  }

  // Open the project sharing dialog
  share() {
    const projectId = this.parent.projectId;
    var userName = "";
    const dialogRef = this.dialog.open(ShareDialog, {
      width: '250px',
      data: {contributors: this.parent.projectContributors}
    });

    // After the dialog is closed try to share the project
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        userName = result;
        console.log("Attempting to share project with " + userName);
        this.http.post(globalVars.baseURL + '/project/share/' + projectId + '/' + userName, []).subscribe(
          retVal => {
          console.log('Shared project with \"' + retVal + '\"');
          this.parent.updateProjectDetails();
          this.showSharingResult("Project was successfully shared with " + userName, "check_circle_outline");
        },
        err => {
          console.log("Could not share project with user");
          this.showSharingResult("Project could not be shared with " + userName, "disabled_by_default")
        }); 
      } else {
        console.log('Project was not shared');
      }
    });
  }

  displayedColumns:string[] = []

  showProjectsList() {
    this.router.navigate(['projects']);
  }

  logout() {
    this.authService.logout();
  }

  newFile() {
    const fileName = '';
    const dialogRef = this.dialog.open(NewFileDialog, {
      width: '250px',
      data: {name: fileName}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const sourceFile: SourceFile = {
          id: '',
          name: result,
          content: '',
          project: {
            id: this.parent.projectId
          }
        };
        this.http.post(globalVars.baseURL + '/project/sourceFile', sourceFile).subscribe((retVal: SourceFile) => {
          console.log('Sourcefile \"' + retVal + '\" was created.');
          // Set the current file to the returned one which will be replaced
          // by updateProjectFiles with the new instance as soon as it is
          // available
          this.parent.showFile(retVal);
          this.parent.updateProjectFiles();
        }); 
      } else {
        console.log('Sourcefile was not created');
      }
    });
  }

  // Popup that the sharing worked / did not work
  showSharingResult(text: string, icon: string) {
    const dialogRef = this.dialog.open(SharingResultDialog, {
      width: '250px',
      data: {text: text, icon: icon}
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  ngOnInit(): void {
  }

}

// Components for the dialogs

@Component({
  selector: 'navigation-drawer-new-file-dialog.component',
  templateUrl: 'navigation-drawer.new-file-dialog.component.html',
})
export class NewFileDialog {

  constructor(
    public dialogRef: MatDialogRef<NewFileDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}
}

@Component({
  selector: 'navigation-drawer-share-dialog.component',
  templateUrl: 'navigation-drawer.share-dialog.component.html',
})
export class ShareDialog {

  constructor(
    public dialogRef: MatDialogRef<ShareDialog>,
    @Inject(MAT_DIALOG_DATA) public data: ShareDialogData) {}
}

@Component({
  selector: 'navigation-drawer-sharing-result-dialog.component',
  templateUrl: 'navigation-drawer.sharing-result-dialog.component.html',
})
export class SharingResultDialog {

  constructor(
    public dialogRef: MatDialogRef<SharingResultDialog>,
    @Inject(MAT_DIALOG_DATA) public data: SharingResultDialogData) {}
}
