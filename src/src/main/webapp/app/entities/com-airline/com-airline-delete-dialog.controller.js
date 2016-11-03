(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_airlineDeleteController',Com_airlineDeleteController);

    Com_airlineDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_airline'];

    function Com_airlineDeleteController($uibModalInstance, entity, Com_airline) {
        var vm = this;
        vm.com_airline = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_airline.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
