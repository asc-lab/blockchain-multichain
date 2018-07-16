import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Block} from "../../shared/model/Block";
import {ChainExplorerService} from "../../shared/chain-explorer.service";
import {Chain} from "../../shared/model/Chain";
import {timer} from "rxjs/index";
import {BaseRefreshComponent} from "../BaseRefreshComponent";

@Component({
  selector: 'app-chain-explorer',
  templateUrl: './chain-explorer.component.html',
  styleUrls: ['./chain-explorer.component.css']
})
export class ChainExplorerComponent extends BaseRefreshComponent implements OnInit {

  blocks: Block[] = [];
  chain: Chain = <Chain> {};

  constructor(private chainExplorer: ChainExplorerService,
              protected cd: ChangeDetectorRef) {
    super(cd);
  }

  ngOnInit() {
    timer(0, 5000)
      .subscribe(() => {
        this.chainExplorer.getAllBlocks().subscribe(res => {
          this.blocks = res;
          super.refreshView();
        });
        this.chainExplorer.getChainMainInfo().subscribe(res => {
          this.chain = res;
          super.refreshView();
        });
      });
  }
}
