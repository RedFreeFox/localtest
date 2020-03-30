import {NgModule}      from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule}   from '@angular/forms';


import {IndexComponent}        from './index.component';
import {IndexRoutingModule} from "./index-routing.module";
import { HttpModule, JsonpModule } from '@angular/http';
import {Config} from "../app-config";
import {MyHeaderComponent} from "./common/my-header.component";

import {TUserModule} from "../baseinfo/t-user.module";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import {AsideComponent} from "../aside/aside.component";

@NgModule({
  imports: [BrowserModule, FormsModule,
    BsDropdownModule.forRoot(),
    IndexRoutingModule,
    HttpModule,
    JsonpModule,
    TUserModule
  ],
  declarations: [IndexComponent, MyHeaderComponent,
    AsideComponent
     ],
  providers: [
    Config
  ]
})
export class IndexModule {

}
