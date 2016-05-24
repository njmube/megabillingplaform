(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_countryDeleteController',C_countryDeleteController);

    C_countryDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_country'];

    function C_countryDeleteController($uibModalInstance, entity, C_country) {
        var vm = this;
        vm.c_country = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_country.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
