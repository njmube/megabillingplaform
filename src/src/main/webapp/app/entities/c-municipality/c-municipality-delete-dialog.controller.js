(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_municipalityDeleteController',C_municipalityDeleteController);

    C_municipalityDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_municipality'];

    function C_municipalityDeleteController($uibModalInstance, entity, C_municipality) {
        var vm = this;
        vm.c_municipality = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_municipality.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
