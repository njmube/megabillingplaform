(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Config_pathrootfileDeleteController',Config_pathrootfileDeleteController);

    Config_pathrootfileDeleteController.$inject = ['$uibModalInstance', 'entity', 'Config_pathrootfile'];

    function Config_pathrootfileDeleteController($uibModalInstance, entity, Config_pathrootfile) {
        var vm = this;
        vm.config_pathrootfile = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Config_pathrootfile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
