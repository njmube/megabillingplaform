(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_acquiring_dataDeleteController',Com_acquiring_dataDeleteController);

    Com_acquiring_dataDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_acquiring_data'];

    function Com_acquiring_dataDeleteController($uibModalInstance, entity, Com_acquiring_data) {
        var vm = this;
        vm.com_acquiring_data = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_acquiring_data.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
