(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_tarDetailController', C_tarDetailController);

    C_tarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_tar'];

    function C_tarDetailController($scope, $rootScope, $stateParams, entity, C_tar) {
        var vm = this;
        vm.c_tar = entity;
        vm.load = function (id) {
            C_tar.get({id: id}, function(result) {
                vm.c_tar = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_tarUpdate', function(event, result) {
            vm.c_tar = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
