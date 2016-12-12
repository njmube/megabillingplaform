(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_acquiring_dataDeleteController',Freecom_acquiring_dataDeleteController);

    Freecom_acquiring_dataDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_acquiring_data'];

    function Freecom_acquiring_dataDeleteController($uibModalInstance, entity, Freecom_acquiring_data) {
        var vm = this;
        vm.freecom_acquiring_data = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_acquiring_data.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
