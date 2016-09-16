(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CfdiDeleteController',CfdiDeleteController);

    CfdiDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cfdi'];

    function CfdiDeleteController($uibModalInstance, entity, Cfdi) {
        var vm = this;

        vm.cfdi = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cfdi.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
