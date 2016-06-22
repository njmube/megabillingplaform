(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_statesDeleteController',Cfdi_statesDeleteController);

    Cfdi_statesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cfdi_states'];

    function Cfdi_statesDeleteController($uibModalInstance, entity, Cfdi_states) {
        var vm = this;

        vm.cfdi_states = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cfdi_states.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
