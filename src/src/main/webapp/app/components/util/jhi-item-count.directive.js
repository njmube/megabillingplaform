(function() {
    'use strict';

    var jhiItemCount = {
        template: '<div class="info">' +
                    'Mostrando {{(($ctrl.page-1) * 20)==0 ? 0:(($ctrl.page-1) * 20)}} - ' +
                    '{{($ctrl.page * 20) < $ctrl.queryCount ? ($ctrl.page * 20) : $ctrl.queryCount}} ' +
                    'de {{$ctrl.queryCount}} elementos.' +
                '</div>',
        bindings: {
            page: '<',
            queryCount: '<total'
        }
    };

    angular
        .module('megabillingplatformApp')
        .component('jhiItemCount', jhiItemCount);
})();
