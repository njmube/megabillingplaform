(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_foreign_tourist_passengerDeleteController',Freecom_foreign_tourist_passengerDeleteController);

    Freecom_foreign_tourist_passengerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_foreign_tourist_passenger'];

    function Freecom_foreign_tourist_passengerDeleteController($uibModalInstance, entity, Freecom_foreign_tourist_passenger) {
        var vm = this;
        vm.freecom_foreign_tourist_passenger = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_foreign_tourist_passenger.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
