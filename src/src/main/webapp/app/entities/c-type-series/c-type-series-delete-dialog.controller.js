(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_seriesDeleteController',C_type_seriesDeleteController);

    C_type_seriesDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_type_series'];

    function C_type_seriesDeleteController($uibModalInstance, entity, C_type_series) {
        var vm = this;

        vm.c_type_series = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            C_type_series.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
