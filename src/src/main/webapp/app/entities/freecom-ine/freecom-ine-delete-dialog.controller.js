(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ineDeleteController',Freecom_ineDeleteController);

    Freecom_ineDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_ine'];

    function Freecom_ineDeleteController($uibModalInstance, entity, Freecom_ine) {
        var vm = this;
        vm.freecom_ine = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_ine.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
