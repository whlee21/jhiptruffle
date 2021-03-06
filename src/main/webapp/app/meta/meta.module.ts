import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UtilModule } from 'app/util/util.module';

import { metaSenderState, MetaSenderComponent } from './';

@NgModule({
    imports: [CommonModule, RouterModule, UtilModule, RouterModule.forChild(metaSenderState)],
    declarations: [MetaSenderComponent]
})
export class MetaModule {}
