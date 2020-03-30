import {NgModule}      from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {InfoComponent} from "../info/info.component";

@NgModule({
  imports: [BrowserModule
  ],
  exports:      [ InfoComponent ],
  declarations: [InfoComponent]
})
export class InfoModule {

}
