(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_series_folioDeleteController',Taxpayer_series_folioDeleteController);

    Taxpayer_series_folioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Taxpayer_series_folio'];

    function Taxpayer_series_folioDeleteController($uibModalInstance, entity, Taxpayer_series_folio) {
        var vm = this;

        vm.taxpayer_series_folio = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Taxpayer_series_folio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
