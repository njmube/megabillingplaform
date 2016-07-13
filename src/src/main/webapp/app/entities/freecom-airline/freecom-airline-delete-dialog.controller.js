(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_airlineDeleteController',Freecom_airlineDeleteController);

    Freecom_airlineDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_airline'];

    function Freecom_airlineDeleteController($uibModalInstance, entity, Freecom_airline) {
        var vm = this;
        vm.freecom_airline = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_airline.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
