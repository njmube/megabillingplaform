(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('School_levelDeleteController',School_levelDeleteController);

    School_levelDeleteController.$inject = ['$uibModalInstance', 'entity', 'School_level'];

    function School_levelDeleteController($uibModalInstance, entity, School_level) {
        var vm = this;
        vm.school_level = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            School_level.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
