import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ProjectListComponent, EditProjectNameDialog } from './project-list/project-list.component';
import { ProjectViewComponent } from './project-view/project-view.component';
import { CodeEditorComponent, EditFileNameDialog } from './code-editor/code-editor.component';
import { HomeComponent } from './home/home.component';
import { NavigationDrawerComponent, NewFileDialog, ShareDialog, SharingResultDialog } from './navigation-drawer/navigation-drawer.component';
import { CompilerOutputComponent } from './compiler-output/compiler-output.component';
import { Route, RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import { MatSliderModule } from '@angular/material/slider';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list'; 
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MonacoEditorModule } from 'ngx-monaco-editor';
import {AuthGuard} from './auth.guard';

const routes: Route[] = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'ide/:id', component: ProjectViewComponent, canActivate: [AuthGuard] },
  { path: 'projects', component: ProjectListComponent, canActivate: [AuthGuard] },
  { path: 'home', component: HomeComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    ProjectListComponent,
    ProjectViewComponent,
    CodeEditorComponent,
    HomeComponent,
    NavigationDrawerComponent,
    CompilerOutputComponent,
    EditProjectNameDialog,
    EditFileNameDialog,
    NewFileDialog,
    ShareDialog,
    SharingResultDialog
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatSliderModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatTableModule,
    MatListModule,
    MatInputModule,
    FormsModule,
    MatDialogModule,
    MonacoEditorModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }