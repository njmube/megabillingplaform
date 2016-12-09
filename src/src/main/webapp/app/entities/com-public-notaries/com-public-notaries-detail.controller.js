(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_public_notariesDetailController', Com_public_notariesDetailController);

    Com_public_notariesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_public_notaries', 'Cfdi'];

    function Com_public_notariesDetailController($scope, $rootScope, $stateParams, entity, Com_public_notaries, Cfdi) {
        var vm = this;
        vm.com_public_notaries = entity;
        vm.load = function (id) {
            Com_public_notaries.get({id: id}, function(result) {
                vm.com_public_notaries = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_public_notariesUpdate', function(event, result) {
            vm.com_public_notaries = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
