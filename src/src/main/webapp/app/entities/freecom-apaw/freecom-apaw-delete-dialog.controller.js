(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_apawDeleteController',Freecom_apawDeleteController);

    Freecom_apawDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_apaw'];

    function Freecom_apawDeleteController($uibModalInstance, entity, Freecom_apaw) {
        var vm = this;
        vm.freecom_apaw = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_apaw.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
