(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_acquired_titleDetailController', C_acquired_titleDetailController);

    C_acquired_titleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_acquired_title'];

    function C_acquired_titleDetailController($scope, $rootScope, $stateParams, entity, C_acquired_title) {
        var vm = this;
        vm.c_acquired_title = entity;
        vm.load = function (id) {
            C_acquired_title.get({id: id}, function(result) {
                vm.c_acquired_title = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_acquired_titleUpdate', function(event, result) {
            vm.c_acquired_title = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
