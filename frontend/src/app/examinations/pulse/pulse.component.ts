import { Component, OnInit, Input } from '@angular/core';
import { Examination } from '../../models/examination';
import { SimpleModalService } from 'ngx-simple-modal';
import { ExaminationModalComponent } from '../../shared/examination-modal/examination-modal.component';

@Component({
  selector: 'app-pulse',
  templateUrl: './pulse.component.html',
  styleUrls: ['./pulse.component.css']
})
export class PulseComponent implements OnInit {

  @Input()
  examination: Examination = {date: new Date(), value: 68, raw_data : []};

  constructor(private modalService : SimpleModalService) { }

  ngOnInit() {
  }

  showDataModal() {
    let disposable = this.modalService.addModal(ExaminationModalComponent, this.examination).subscribe(
      // data => disposable.unsubscribe()
    );
    // setTimeout(() => disposable.unsubscribe(), 10000);
  }
}
