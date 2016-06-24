(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ComplementDetailController', ComplementDetailController);

    ComplementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Complement'];

    function ComplementDetailController($scope, $rootScope, $stateParams, entity, Complement) {
        var vm = this;
        vm.complement = entity;
        vm.load = function (id) {
            Complement.get({id: id}, function(result) {
                vm.complement = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:complementUpdate', function(event, result) {
            vm.complement = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
