(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_legendDeleteController',Freecom_legendDeleteController);

    Freecom_legendDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_legend'];

    function Freecom_legendDeleteController($uibModalInstance, entity, Freecom_legend) {
        var vm = this;

        vm.freecom_legend = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_legend.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
