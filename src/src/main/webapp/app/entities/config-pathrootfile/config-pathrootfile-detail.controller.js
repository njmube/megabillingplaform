(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Config_pathrootfileDetailController', Config_pathrootfileDetailController);

    Config_pathrootfileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Config_pathrootfile'];

    function Config_pathrootfileDetailController($scope, $rootScope, $stateParams, entity, Config_pathrootfile) {
        var vm = this;
        vm.config_pathrootfile = entity;
        vm.load = function (id) {
            Config_pathrootfile.get({id: id}, function(result) {
                vm.config_pathrootfile = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:config_pathrootfileUpdate', function(event, result) {
            vm.config_pathrootfile = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
