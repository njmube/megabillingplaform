(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_foreign_tourist_passengerDeleteController',Com_foreign_tourist_passengerDeleteController);

    Com_foreign_tourist_passengerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_foreign_tourist_passenger'];

    function Com_foreign_tourist_passengerDeleteController($uibModalInstance, entity, Com_foreign_tourist_passenger) {
        var vm = this;
        vm.com_foreign_tourist_passenger = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_foreign_tourist_passenger.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
